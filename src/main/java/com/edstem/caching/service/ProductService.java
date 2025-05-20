package com.edstem.caching.service;

import com.edstem.caching.model.Product;
import com.edstem.caching.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

		private final ProductRepository productRepository;

		public ProductService(ProductRepository productRepository) {
			this.productRepository = productRepository;
		}

		public Product createProduct(Product product) {
			return productRepository.save(product);
		}

		@Cacheable(value = "products", key = "#id")
		public Product getProductById(Long id) {
			return productRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Product not found."));
		}


		@Cacheable(value = "prices", key = "#id")
		public Double getPrice(Long id) {
			Product product = productRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Product not found."));
			return product.calculatePrice();
		}

		@CachePut(value = "products", key = "#id")
		@CacheEvict(value = "prices", key = "#id")
		public Product updateProduct(Long id, Product product) {
			Product existingProduct = productRepository.findById(id)
					.orElseThrow(() -> new RuntimeException("Product not found."));

			existingProduct.setName(product.getName());
			existingProduct.setBasePrice(product.getBasePrice());
			existingProduct.setDiscount(product.getDiscount());
			return productRepository.save(existingProduct);
		}

		@CacheEvict(value = {"products", "prices"}, allEntries = true)
		public void clearAllCache() {
			System.out.println("Clearing all caches");
		}
}
