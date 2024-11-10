import { Component, Input, OnInit } from '@angular/core';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-ver-revista',
  standalone: true,
  imports: [],
  templateUrl: './ver-revista.component.html',
  styleUrl: './ver-revista.component.css',
})
export class VerRevistaComponent implements OnInit {
  private apiUrl = 'http://localhost:8080/Backend/resources/Revista';
  @Input() path: string = '';
  sanitizedUrl: SafeResourceUrl | null = null;

  constructor(private sanitizer: DomSanitizer) {}

  ngOnInit(): void {
      this.obtenerLink(); // Llama a la función al iniciar el componente
  } 
  
  private obtenerLink(): void {
      if (this.path) {
          const url = `${this.apiUrl}/verRevista?path=${encodeURIComponent(this.path)}`;
          this.sanitizedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(url);
      } else {
          this.sanitizedUrl = null; // Resetea si no hay path
      }
  }
}
