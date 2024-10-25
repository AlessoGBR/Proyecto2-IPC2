import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ListaEsperaComponent } from 'app/Componentes/compAdmin/lista-espera/lista-espera/lista-espera.component';
import { ListaDenegadasComponent } from 'app/Componentes/compAdmin/lista-denegadas/lista-denegadas/lista-denegadas.component';
import { ListaAprobadasComponent } from 'app/Componentes/compAdmin/lista-aprobadas/lista-aprobadas/lista-aprobadas.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-menu-admin',
  standalone: true,
  imports: [ListaEsperaComponent,RouterOutlet, CommonModule, ListaAprobadasComponent, ListaDenegadasComponent],
  templateUrl: './menu-admin.component.html',
  styleUrl: './menu-admin.component.css'
})
export class MenuAdminComponent {

  espera: boolean = true;
  rechazados: boolean = false;

}
