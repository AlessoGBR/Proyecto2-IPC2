import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnuncioService {

  private apiUrl = 'http://localhost:8080/Backend/resources/RegistroAnuncio'; // URL del backend

  constructor(private http: HttpClient) {}

  registrarAnuncio(anuncioData: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(this.apiUrl, anuncioData, { headers });
  }
}
