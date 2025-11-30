package com.example.SmartShop.model.entitie;

import com.example.SmartShop.model.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Client extends User {

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(length = 20)
    private String telephone;

    @Column(columnDefinition = "TEXT")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CustomerTier tier = CustomerTier.BASIC;

    @Column(name = "total_orders")
    private Integer totalOrders = 0;

    @Column(name = "total_spent", precision = 12, scale = 2)
    private BigDecimal totalSpent = BigDecimal.ZERO;

    @Column(name = "first_order_date")
    private LocalDate firstOrderDate;

    @Column(name = "last_order_date")
    private LocalDate lastOrderDate;


    @Column(name = "ice", unique = true)
    private String ice;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @Override
    public String getRole() {
        return "CLIENT";
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public static class ClientBuilder {
        private String username;
        private String password;
        private String nom;
        private String email;
        private String telephone;
        private String adresse;
        private String companyName;
        private String ice;
        private Boolean active = true;

        public ClientBuilder username(String username) {
            this.username = username;
            return this;
        }

        public ClientBuilder password(String password) {
            this.password = password;
            return this;
        }

        public ClientBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public ClientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public ClientBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }

        public ClientBuilder adresse(String adresse) {
            this.adresse = adresse;
            return this;
        }

        public ClientBuilder companyName(String companyName) {
            this.companyName = companyName;
            return this;
        }

        public ClientBuilder ice(String ice) {
            this.ice = ice;
            return this;
        }

        public ClientBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Client build() {
            Client client = new Client();
            client.setUsername(username);
            client.setPassword(password);
            client.setNom(nom);
            client.setEmail(email);
            client.setTelephone(telephone);
            client.setAdresse(adresse);
            client.setIce(ice);
            client.setActive(active);
            return client;
        }
    }
}