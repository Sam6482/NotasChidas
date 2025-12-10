# [Notas Chidas]

> *Proyecto Integrador - Desarrollo de Aplicaciones Móviles*
> 
>*Cuatrimestre:* 4°E
> *Fecha de entrega:* 11 de Diciembre

---

## Equipo de Desarrollo

| Nombre Completo | Rol / Tareas Principales | Usuario GitHub |
| :--- | :--- | :--- |
| Samuel Rivera Robles | Repositorio, Viewmodel, Screens de Añadir y Editar, File Paths | @Sam6482 |
| Leonardo Lopez Pacheco | Retrofit, Api Service, Data Model, Permisos, Gradle | @LeonardoLp178 |
| Carlos Maximiliano Martinez Roman | Sensores, Manejo de Uri's, Screen de vista general, Navegacion | @usuario3 |

---

## Descripción del Proyecto

*¿Qué hace la aplicación?*
El proyecto es una aplicación de notas la cual permite realizar notas básicas agregando imagenes incluso, de galeria o tomadas con la camara,
Haciendo uso de Retrofit para un servidor local permitiendo las funcionalidades de un CRUD y usando el sensor giroscopio para borrar notas seleccionadas agitando el telefono.

*Objetivo:*
Demostrar la implementación de una arquitectura robusta en Android utilizando servicios web y hardware del dispositivo.

---

## Stack Tecnológico y Características

Este proyecto ha sido desarrollado siguiendo estrictamente los lineamientos de la materia:

* *Lenguaje:* Kotlin 100%.
* *Interfaz de Usuario:* Jetpack Compose.
* *Arquitectura:* MVVM (Model-View-ViewModel).
* *Conectividad (API REST):* Retrofit.
    * *GET:* Obtiene una lista completa de todas las notas disponibles en el servidor.
    * *POST:* Envía datos para crear una nueva nota en el servidor.
    * *UPDATE:* Actualiza una nota existente en el servidor, identificada por su id.
    * *DELETE:* Elimina una nota existente del servidor.
* *Sensor Integrado:* Giroscopio
    * Uso: Borrar notas seleccionadas, agitando el teléfono.

---

## Capturas de Pantalla

[Coloca al menos 3 (investiga como agregarlas y se vean en GitHub)]

| Pantalla de Inicio | Operación CRUD | Uso del Sensor |
| :---: | :---: | :---:

|![WhatsApp Image 2025-12-09 at 21 49 40 (2)](https://github.com/user-attachments/assets/0b2bbe27-9830-4a77-b417-92c8c2472e52)|![WhatsApp Image 2025-12-09 at 21 49 40](https://github.com/user-attachments/assets/21754212-97e6-4590-b862-6bcbf13a6f9d)
| | ![WhatsApp Image 2025-12-09 at 21 49 40 (1)](https://github.com/user-attachments/assets/908d5f9f-c1d8-473f-856b-7a0da493fb40)|

---

## Instalación y Releases

El ejecutable firmado (.apk) se encuentra disponible en la sección de *Releases* de este repositorio.

1.  Ve a la sección "Releases" (o haz clic [aquí](https://github.com/Sam6482/NotasChidas/releases/tag/App)).
2.  Descarga el archivo .apk de la última versión.
3.  Instálalo en tu dispositivo Android (asegúrate de permitir la instalación de orígenes desconocidos).
