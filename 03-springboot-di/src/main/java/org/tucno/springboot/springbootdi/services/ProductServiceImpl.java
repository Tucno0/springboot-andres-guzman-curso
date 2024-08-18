package org.tucno.springboot.springbootdi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.tucno.springboot.springbootdi.models.Product;
import org.tucno.springboot.springbootdi.repositories.ProductRepository;

import java.util.List;

// @Component es una anotación que se utiliza para indicar que la clase es un componente de Spring y se debe registrar en el contenedor de Spring
//@Component

// @Service es casi lo mismo que @Component, pero se utiliza para indicar que la clase es un servicio de Spring y se debe registrar en el contenedor de Spring
// Un servicio es una clase que se utiliza para contener la lógica de negocio de una aplicación y se debe registrar en el contenedor de Spring.
@Service
public class ProductServiceImpl implements ProductService {
    // @Autowired es una anotación que se utiliza para inyectar dependencias en Spring automáticamente
    // Hay varias formas de inyectar dependencias en Spring con @Autowired
    // 1. Inyección de dependencias por constructor
    // 2. Inyección de dependencias por método setter
    // 3. Inyección de dependencias por campo

    // Inyección de dependencias por campo
//    @Autowired
//    @Qualifier("productRepositoryImpl")
    ProductRepository productRepository;

    @Value("${config.price.tax}")
    private Double tax;

    @Autowired
    private Environment environment;

    // Inyección de dependencias por método setter
//    @Autowired
//    public void setProductRepository(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    // Inyección de dependencias por constructor
    @Autowired // No es necesario, pero se puede utilizar para indicar que el constructor es un punto de inyección de dependencias

    // @Qualifier es una anotación que se utiliza para indicar cuál de las implementaciones de una interfaz se debe inyectar cuando hay varias implementaciones de una interfaz
//    @Qualifier("productRepositoryImpl")
    public ProductServiceImpl(@Qualifier("productRepositoryJson") ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll().stream().map(product -> {
            Double priceTax = product.getPrice() * this.tax;
//            product.setPrice(priceTax);
//            return  product;

            // Se debe retornar un nuevo objeto Product con el precio incrementado, esto para no modificar el objeto original y mantener la inmutabilidad
//            return new Product(product.getId(), product.getName(), priceTax);

            // Se clona el objeto original y se modifica el precio del objeto clonado
            Product newProduct = (Product) product.clone();
            newProduct.setPrice(priceTax);
            return newProduct;
        }).toList();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id);
    }
}
