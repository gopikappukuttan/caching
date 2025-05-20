package com.edstem.caching.controller;

import com.edstem.caching.model.Product;
import com.edstem.caching.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return productService.createProduct(product);
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Product getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}

	@GetMapping("/{id}/price")
	@ResponseStatus(HttpStatus.OK)
	public Double getPrice(@PathVariable Long id) {
		return productService.getPrice(id);
	}

	@PutMapping("/{id}")
	public Product updateProduct(@PathVariable Long id,
								 @RequestBody Product product) {
		return productService.updateProduct(id, product);
	}

	@DeleteMapping("/cache")
	public String clearAllCache() {
		productService.clearAllCache();
		return "Caches cleared";
	}
}
