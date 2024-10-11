import { Component,Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Info } from 'app/Objetos/Info';


@Component({
  selector: 'app-error',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css'
})
export class ErrorComponent {

  @Input() mensaje: Info;
  @Input() espera: boolean = false;

  constructor() { 
    this.mensaje = { operacion: false, tipoError: '', mensaje: '' };
  }

  ngOnInit(): void {
  }
}
