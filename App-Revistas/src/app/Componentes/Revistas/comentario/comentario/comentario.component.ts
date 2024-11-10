import { Component, OnInit, Input } from '@angular/core';
import { Comentario } from 'app/Objetos/Comentario';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-comentario',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './comentario.component.html',
  styleUrl: './comentario.component.css',
})
export class ComentarioComponent implements OnInit {
  @Input() comentario!: Comentario;
  constructor() {}
  ngOnInit(): void {}
}
