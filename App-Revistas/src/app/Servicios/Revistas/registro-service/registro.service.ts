import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Revista } from 'app/Objetos/Revista';


@Injectable({
  providedIn: 'root'
})
export class RegistroService {

  private apiUrl = 'http://localhost:8080/Backend/resources/RegistroRevista'; 
  
  constructor(private http: HttpClient) { }

  registrarRevista(revista: Revista): Observable<any> {
    const body = {
      ...revista,
      
    };
    return this.http.post(this.apiUrl, body, {
      headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' }
    });
  }
  
  cambioComentario(idRevista: number, comentario: boolean): Observable<any> {
    const body = {
      idRevista: idRevista,
      tieneComentarios: comentario
    };
  
    return this.http.put<any>(`${this.apiUrl}/actualizarComentarios/${idRevista}`, body, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  cambioMeGusta(idRevista: number, comentario: boolean): Observable<any> {
    const body = {
      idRevista: idRevista,
      tieneMeGusta: comentario
    };
  
    return this.http.put<any>(`${this.apiUrl}/actualizarMeGusta/${idRevista}`, body, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  cambioSuscripciones(idRevista: number, comentario: boolean): Observable<any> {
    const body = {
      idRevista: idRevista,
      tieneSuscripciones: comentario
    };
  
    return this.http.put<any>(`${this.apiUrl}/actualizarSuscripciones/${idRevista}`, body, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  aceptarRevista(idRevista: number): Observable<any> {
    const body = {
      idRevista: idRevista
    };
  
    return this.http.put<any>(`${this.apiUrl}/actualizarAprobada/${idRevista}`, body, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  denegarRevista(idRevista: number): Observable<any> {
    const body = {
      idRevista: idRevista
    };
  
    return this.http.put<any>(`${this.apiUrl}/actualizarDenegada/${idRevista}`, body, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  
}
