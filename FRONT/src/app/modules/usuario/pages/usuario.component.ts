import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UsuarioService } from '../service/usuario.service';

export interface ApiResponse {
  message: string;
  id?: number;
}

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss'],
})
export class UsuarioComponent implements OnInit {
  userForm: FormGroup;
  roles = ['admin', 'comprador'];
  successMessage = '';
  errorMessage = '';
  isEditMode = false; 
  currentUserId: number | null = null; 

  usuarios: any[] = [];

  constructor(private fb: FormBuilder, private usuarioService: UsuarioService) {
    this.userForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      role: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
    });
  }

  ngOnInit() {
    this.getUsuarios();
  }

  getUsuarios() {
    this.usuarioService.getUsers().subscribe({
      next: (data) => {
        this.usuarios = data;
        this.errorMessage = '';
      },
      error: (err) => {
        this.errorMessage = `Error al cargar los usuarios: ${err.message}`;
      },
    });
  }

  onSubmit() {
    if (this.userForm.valid) {
      const userData = this.userForm.value;
  
      if (this.isEditMode) {
        this.usuarioService.updateUser(this.currentUserId!, userData).subscribe({
          next: (response) => {
            console.log('Respuesta completa del servidor:', response); 
            if (response && response.token) {
              this.successMessage = 'Usuario actualizado correctamente';
              this.errorMessage = '';
              this.isEditMode = false;
              this.currentUserId = null;
              this.userForm.reset();
              this.getUsuarios(); 
              alert('¡Usuario actualizado exitosamente!');
            } else {
              this.errorMessage = 'Error inesperado al actualizar el usuario.';
              alert('Error inesperado al actualizar el usuario.');
            }
          },
          error: (err) => {
            console.error('Error:', err); 
            this.errorMessage = err?.error?.message || 'Error desconocido al actualizar el usuario.';
            this.successMessage = '';
            alert('Ocurrió un error al actualizar el usuario.');
          },
        });
      } else {
        this.usuarioService.registerUser(userData).subscribe({
          next: (response) => {
            console.log('Respuesta completa del servidor:', response); 
            if (response && response.token) {
              this.successMessage = 'Usuario creado correctamente';
              this.errorMessage = '';
              this.userForm.reset();
              this.getUsuarios(); 
              alert('¡Usuario creado exitosamente!');
            } else {
              this.errorMessage = 'Error inesperado al crear el usuario.';
              alert('Error inesperado al crear el usuario.');
            }
          },
          error: (err) => {
            console.error('Error:', err);  
            this.errorMessage = err?.error?.message || 'Error desconocido al crear el usuario.';
            this.successMessage = '';
            alert('Ocurrió un error al crear el usuario.');
          },
        });
      }
    } else {
      this.errorMessage = 'Formulario inválido. Por favor, complete los campos correctamente.';
      this.successMessage = '';
      alert('Por favor, complete los campos correctamente.');
    }
  }
  
  editUser(user: any) {
    this.isEditMode = true;
    this.currentUserId = user.id;
    this.userForm.patchValue({
      username: user.username,
      firstname: user.firstname,
      lastname: user.lastname,
      email: user.email,
      role: user.role,
    });
  }
  deleteUser(id: number) {
    if (confirm('¿Estás seguro de que deseas eliminar este usuario?')) {
      this.usuarioService.deleteUser(id).subscribe({
        next: (response: ApiResponse) => {
          console.log('Respuesta del servidor:', response);  
          if (response.message === 'Usuario eliminado exitosamente') {
            this.successMessage = response.message;
            this.errorMessage = '';
            this.getUsuarios(); 
            alert('¡Usuario eliminado exitosamente!');
          } else {
            this.errorMessage = 'Error inesperado al eliminar el usuario.';
            alert('Error inesperado al eliminar el usuario.');
          }
        },
        error: (err: any) => {
          console.error('Error en la solicitud:', err);  
          this.errorMessage = err?.error?.message || 'Error desconocido al eliminar el usuario.';
          this.successMessage = '';
          alert('Ocurrió un error al eliminar el usuario.');
        },
      });
    }
  }

}
