package ec.edu.uteq.inventariouteq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;



@SpringBootApplication
@Controller
public class InventarioUteqApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventarioUteqApplication.class, args);
    }



    private static List<Product> productos = new ArrayList<>();
    private static Long idCounter = 1L;

    static {
        productos.add(new Product(idCounter++, "Laptop HP", "16GB RAM, SSD 512GB", 850.0, 15));
        productos.add(new Product(idCounter++, "Monitor 24'", "Full HD 75Hz", 150.0, 8));
    }

    @GetMapping("/")
    public String index(Model model) {
        // LOG REPETIDO
        System.out.println("[DEBUG LOG] Acceso al index - Registrado el: " + new java.util.Date());
        model.addAttribute("lista", productos);
        model.addAttribute("prod", new Product());
        return "index";
    }

    @PostMapping("/guardar")
    public String guardar(Product p) {
        // LOG REPETIDO
        System.out.println("[DEBUG LOG] Guardando producto: " + p.getNombre() + " - Fecha: " + new java.util.Date());

        // Lógica mezclada
        if (p.getId() == null) {
            p.setId(idCounter++);
            productos.add(p);
        } else {
            for (Product item : productos) {
                if (item.getId().equals(p.getId())) {
                    item.setNombre(p.getNombre());
                    item.setDescripcion(p.getDescripcion());
                    item.setPrecio(p.getPrecio());
                    item.setStock(p.getStock());
                }
            }
        }
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String borrar(@PathVariable Long id) {
        System.out.println("[DEBUG LOG] Eliminando ID: " + id + " - Fecha: " + new java.util.Date());
        productos.removeIf(x -> x.getId().equals(id));
        return "redirect:/";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Product encontrado = productos.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        model.addAttribute("prod", encontrado);
        model.addAttribute("lista", productos);
        return "index";
    }
}


class Product {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;

    public Product() {}
    public Product(Long id, String nombre, String descripcion, Double precio, Integer stock) {
        this.id = id; this.nombre = nombre; this.descripcion = descripcion; this.precio = precio; this.stock = stock;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}


