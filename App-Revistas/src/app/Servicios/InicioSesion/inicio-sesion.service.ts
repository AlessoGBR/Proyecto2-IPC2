import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InicioSesionService {

  private apiUrl = 'http://localhost:8080/Backend/resources'

  constructor(private http: HttpClient) {}

  inicioSesion(username: string, password: string): Observable<any> {
    const body = { username, password };

    return this.http.post(`${this.apiUrl}/InicioSesion`, body, {
      withCredentials: true 
  });
  }
}
