import { CommonModule, formatCurrency, formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet, Router } from '@angular/router';
import { InicioComponent } from '../inicio.component';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { InicioSesionService } from 'app/Servicios/InicioSesion/inicio-sesion.service';
import { AlmacenamientoService } from 'app/Servicios/Revistas/almacenamiento-service/almacenamiento-service.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-inicio-sesion',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterLink, RouterLinkActive, InicioComponent, ReactiveFormsModule],
  templateUrl: './inicio-sesion.component.html',
  styleUrl: './inicio-sesion.component.css'
})
export class InicioSesionComponent implements OnInit {

  validarForm: FormGroup;
  username = "";
  password = "";

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private InicioSesionService : InicioSesionService,
    private AlmacenamientoService: AlmacenamientoService
  ) {
    this.validarForm = this.formBuilder.group({
      nombreUsuario: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  onSubmit() {
    if (this.validarForm.valid) {
      const formData = {
        username: this.validarForm.get('nombreUsuario')?.value,
        password: this.validarForm.get('password')?.value
      };
      console.log('Datos a enviar:', formData);
  
      this.InicioSesionService.inicioSesion(formData.username, formData.password).subscribe(
        (response) => {
          console.log('Inicio de sesión exitoso:', response);
          const userType = response.userType;
          console.log('tipo de usuario',userType);
          sessionStorage.setItem('username', formData.username);
          this.AlmacenamientoService.guardarUsuario(formData.username);
          console.log(this.AlmacenamientoService.obtenerUsuario)
          if (userType === 1) {
            this.router.navigate(['/InicioAdmin']); 
          } else if (userType === 2) {            
            this.router.navigate(['/InicioLector']);
          } else if (userType === 3) {
            this.router.navigate(['/InicioEditor']);
          }else if (userType === 4) {
            this.router.navigate(['/InicioAnunciante']);
          }else {
            this.router.navigate(['/Inicio']); 
          }
        },
        (error) => {
          console.error('Error en el inicio de sesión:', error);
          Swal.fire({
            icon: 'error',
            title: 'Usuario o contraseña invalidos',
            text: 'Intenta nuevamente.',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    } else {
      console.error('El formulario no es válido');
    }
  }

}
