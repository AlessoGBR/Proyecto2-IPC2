import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comentario } from 'app/Objetos/Comentario';

@Injectable({
  providedIn: 'root'
})
export class ComentarioService{

  private apiUrl = 'http://localhost:8080/Backend/resources/Comentarios';

  constructor(private http: HttpClient) {}

  obtenerComentarios(idRevista: number): Observable<Comentario[]> {
    return this.http.get<Comentario[]>(`${this.apiUrl}/ObtenerComentarios/${idRevista}`);
  }
}
