import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { InicioComponent } from "../../../inicio/inicio.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menu-lector',
  standalone: true,
  imports: [RouterOutlet, InicioComponent],
  templateUrl: './menu-lector.component.html',
  styleUrl: './menu-lector.component.css'
})
export class MenuLectorComponent {

  mostrarComponente = false; 
}
