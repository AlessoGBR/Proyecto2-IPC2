import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reaccion } from 'app/Objetos/Reaccion';

@Injectable({
  providedIn: 'root'
})
export class ReaccionService{

  private apiUrl = 'http://localhost:8080/Backend/resources/Reacciones';

  constructor(private http: HttpClient) {}

  obtenerReacciones(idRevista: number): Observable<Reaccion[]> {
    return this.http.get<Reaccion[]>(`${this.apiUrl}/ObtenerReacciones/${idRevista}`);
  }

  registrarReaccion(reaccion: Reaccion): Observable<any> {
    return this.http.post(`${this.apiUrl}`, reaccion);
  }
}
