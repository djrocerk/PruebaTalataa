# PruebaTalataa
# Código-PRO-00x: Servicio de E-commerce

## Versión 1.0  
**Fecha de creación:** 19-11-2024  
**Uso:** Público  

---

## 1. OBJETIVO
El objetivo de este proyecto es evaluar los conocimientos y desempeño en el desarrollo de una aplicación de e-commerce utilizando **Spring Boot** para el backend y **Angular** como framework frontend basado en **Node.js** y **TypeScript**.

---

## 2. ALCANCE
El sistema desarrollado permite gestionar las siguientes funcionalidades principales:

### **Funcionalidades**
1. **Gestión de Productos:**
   - CRUD completo.
   - Búsqueda avanzada por diferentes criterios.

2. **Gestión de Inventarios:**
   - Actualización automática con cada orden.
   - Reportes sobre disponibilidad.

3. **Gestión de Órdenes:**
   - Descuentos dinámicos basados en reglas de negocio.
   - Generación de reportes para análisis.

### **Producto Mínimo Viable (MVP)**
- **Autenticación:**
  - Login con JWT.
  - Creación y gestión de usuarios con diferentes roles.
- **Operaciones CRUD:**
  - Para productos, inventarios y órdenes.
- **Reportes:**
  - Productos activos.
  - Top 5 de los productos más vendidos.
  - Top 5 de los clientes frecuentes.
- **Búsqueda:**
  - Por múltiples criterios como nombre, categoría, precio, etc.
- **Auditoría:**
  - Registro de acciones clave para trazabilidad.
  
### **Casos Especiales de Funcionamiento**
1. **Descuento Temporal:**  
   - Todas las órdenes dentro de un rango de tiempo definido tendrán un descuento del **10%**.
   
2. **Pedidos Aleatorios:**  
   - Ofrece un descuento del **50%** si la orden queda registrada en el rango definido.

3. **Clientes Frecuentes:**  
   - Descuento adicional del **5%** para clientes frecuentes.

---

## 3. OBLIGATORIOS
- **Versionamiento:**  
  Gestión del código con Git siguiendo estrategias como GitFlow.

- **Pruebas Unitarias:**  
  Realizadas para backend (JUnit) y frontend (Karma/Jasmine).

---

## 4. PLUS (Deseables)
- **CI/CD:**  
  Implementado con GitHub Actions para integración y despliegue continuo.

- **Contenerización:**  
  Uso de Docker para backend y frontend.

- **Análisis Estático:**  
  Herramientas como SonarQube para garantizar la calidad del código.

---

## 5. TECNOLOGÍAS UTILIZADAS
- **Frontend:**
  - Angular (Node.js, TypeScript).
  - Bootstrap para diseño responsivo.
  
- **Backend:**
  - Spring Boot.
  - JPA/Hibernate para persistencia.

- **Base de datos:**
  - MySQL (configuración incluida en `application.properties`).

- **Contenerización:**
  - Docker Compose para orquestación.

- **Autenticación:**
  - JWT para la seguridad.

---

## 6. INSTRUCCIONES DE EJECUCIÓN

### **Backend:**
1. Clonar el repositorio:
   ```bash
   git clone <url-del-repositorio>
   cd backend
