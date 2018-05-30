package ch.martinelli.control.service;

import ch.martinelli.entity.Product;
import ch.martinelli.boundary.CheckController;
import ch.martinelli.control.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(CheckController checkController, ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

}
