package com.example.SmartShop.model.entitie;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Admin extends User {

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;


    @ElementCollection
    @CollectionTable(
            name = "admin_permissions",
            joinColumns = @JoinColumn(name = "admin_id")
    )
    @Column(name = "permission", length = 50)
    private List<String> permissions = new ArrayList<>();

    @Override
    public String getRole() {
        return "ADMIN";
    }


    public static AdminBuilder builder() {
        return new AdminBuilder();
    }

    public static class AdminBuilder {
        private String username;
        private String password;
        private String nom;
        private String prenom;
        private String department;
        private String employeeId;
        @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER)
        private List<String> permissions = new ArrayList<>();
        private Boolean active = true;

        public AdminBuilder username(String username) {
            this.username = username;
            return this;
        }

        public AdminBuilder password(String password) {
            this.password = password;
            return this;
        }

        public AdminBuilder nom(String nom) {
            this.nom = nom;
            return this;
        }

        public AdminBuilder prenom(String prenom) {
            this.prenom = prenom;
            return this;
        }

        public AdminBuilder department(String department) {
            this.department = department;
            return this;
        }

        public AdminBuilder employeeId(String employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public AdminBuilder permissions(List<String> permissions) {
            this.permissions = permissions;
            return this;
        }

        public AdminBuilder active(Boolean active) {
            this.active = active;
            return this;
        }

        public Admin build() {
            Admin admin = new Admin();
            admin.setUsername(username);
            admin.setPassword(password);
            admin.setNom(nom);
            admin.setPrenom(prenom);
            admin.setPermissions(permissions);
            admin.setActive(active);
            return admin;
        }
    }
}