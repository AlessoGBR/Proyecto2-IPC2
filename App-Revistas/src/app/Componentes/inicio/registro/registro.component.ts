import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators,ReactiveFormsModule} from '@angular/forms';
import { DomSanitizer } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ErrorComponent } from "../../Errores/error/error.component";
import { RegistroService } from 'app/Servicios/ServicioRegistro/Registro.service';
import { SeleccionEtiquetasComponent } from 'app/Componentes/Seleccion-etiquetas/selecion-etiquetas/seleccion-etiquetas/seleccion-etiquetas.component';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { InicioComponent } from '../inicio.component';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-registro',
  standalone: true,
  imports: [ErrorComponent, CommonModule, ReactiveFormsModule, SeleccionEtiquetasComponent, InicioComponent],
  templateUrl: './registro.component.html',
  styleUrl: './registro.component.css'
})
export class RegistroComponent {
  validarForm: FormGroup;
  passwordEquals: boolean = false;
  mostrarFoto: string = '';
  foto: File | null = null;
  tipoUsuarios: string[] = ['Lector', 'Administrador', 'Editor', 'Anunciante'];
  tipoUsuario: string = "Lector";
  etiquetasSeleccionadas: Etiqueta[] = [];

  constructor(
    private formBuilder: FormBuilder,
    private sanitizer: DomSanitizer,
    private registroService: RegistroService,
    private router: Router
  ) {
    this.validarForm = this.formBuilder.group({
      nombreUsuario: ['', Validators.required],
      password: ['', Validators.required],
      passwordConfirm: ['', Validators.required],
      tipoUsuario: [, Validators.required],
      descripcion: ['', Validators.required],
      archivo: ['', Validators.required],
    });

  }


  ngOnInit(): void {}


  obtenerImagen(event: Event) {
    const files = (event.target as HTMLInputElement).files;
    if (files && files.length > 0) {
      this.foto = files[0];
      const reader = new FileReader();
      reader.readAsDataURL(this.foto);
      reader.onload = () => {
        this.mostrarFoto = this.sanitizer.bypassSecurityTrustUrl(reader.result as string) as string;
      };
    }
  }

  comprobarPasswords(event: Event) {
    const confirmPassword = (event.target as HTMLInputElement).value;
    this.passwordEquals = confirmPassword === this.validarForm.get('password')?.value;
  }

  actualizarEtiquetas(etiquetas: Etiqueta[]) {
    this.etiquetasSeleccionadas = etiquetas;
  }

  onSubmit() {
    if (this.passwordEquals) {
      const formData = {
        username: this.validarForm.get('nombreUsuario')?.value,
        password: this.validarForm.get('password')?.value,
        descripcion: this.validarForm.get('descripcion')?.value,
        userType: this.tipoUsuario,
        etiquetas: this.etiquetasSeleccionadas
      };
      console.log('Datos a enviar:', formData);
  
      this.registroService.registrarUsuario(formData).subscribe(
        (response) => {
          console.log('Registro exitoso:', response);
          Swal.fire({
            icon: 'success',
            title: 'Registro exitoso',
            text: '¡Bienvenido!',
            confirmButtonText: 'Aceptar'
          }).then((result) => {
            if (result.isConfirmed) {
              this.router.navigate(['/InicioSesion']);
            }
          });
        },
        (error) => {
          console.error('Error en el registro:', error);
          Swal.fire({
            icon: 'error',
            title: 'Error en el registro',
            text: 'Intenta nuevamente.',
            confirmButtonText: 'Aceptar'
          });
        }
      );
    } else {
      console.error('El formulario no es válido');
    }
  }

  onTipoUsuarioChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.tipoUsuario = selectElement.value; 
  }
  


}
