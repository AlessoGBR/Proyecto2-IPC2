import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Etiqueta } from 'app/Objetos/Etiqueta';

@Injectable({
  providedIn: 'root'
})
export class EtiquetaService {

  private apiUrl = 'http://localhost:8080/Backend/resources/Etiquetas'; 

  constructor(private http: HttpClient) {}

  obtenerEtiquetas(): Observable<Etiqueta[]> {
    return this.http.get<Etiqueta[]>(this.apiUrl); 
  }
}
