import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, of } from 'rxjs';
import { Revista } from 'app/Objetos/Revista';
import { Etiqueta } from 'app/Objetos/Etiqueta';
import { Anuncio } from 'app/Objetos/Anuncio';

@Injectable({
  providedIn: 'root',
})
export class ObtenerObjetosService {
  private apiUrl = 'http://localhost:8080/Backend/resources/ObtenerObjetos';

  constructor(private http: HttpClient) {}

  obtenerRevistasPorEditor(usuario: string | null = null): Observable<Revista[]> {
    return this.http
      .post<Revista[]>(`${this.apiUrl}/revistasPorEditor`, usuario, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<Revista[]>('obtenerRevistasPorEditor', []))
      );
  }

  obtenerRevistasUsuario(): Observable<Revista[]> {
    return this.http
      .get<Revista[]>(`${this.apiUrl}/revistasLector`, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<Revista[]>('obtenerRevistasUsuario', []))
      );
  }

  obtenerRevistasBusqueda(etiquetas: Etiqueta[]): Observable<Revista[]> {
    return this.http
      .post<Revista[]>(`${this.apiUrl}/revistasBusqueda`, etiquetas, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<Revista[]>('obtenerRevistasBusqueda', []))
      );
  }

  obtenerRevistasPendientes(): Observable<Revista[]> {
    return this.http
      .get<Revista[]>(`${this.apiUrl}/revistasPendientes`, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<Revista[]>('obtenerRevistasPendientes', []))
      );
  }

  obtenerRevistasAprobadas(): Observable<Revista[]> {
    return this.http
      .get<Revista[]>(`${this.apiUrl}/revistasAprobadas`, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<Revista[]>('obtenerRevistasAprobadas', []))
      );
  }

  obtenerPerfilUsuario(usuario: string): Observable<any> {
    return this.http
      .post<any>(
        `${this.apiUrl}/obtenerPerfil`,
        { usuario },
        {
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .pipe(catchError(this.handleError<any>('obtenerPerfilUsuario', null)));
  }

  obtenerCliente(usuario: string): Observable<any> {
    return this.http
      .post<any>(
        `${this.apiUrl}/obtenerCliente`,
        { usuario },
        {
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .pipe(catchError(this.handleError<any>('obtenerCliente', null)));
  }

  obtenerReacciones(idRevista: number): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.apiUrl}/reacciones?idRevista=${idRevista}`)
      .pipe(catchError(this.handleError<any[]>('obtenerReacciones', [])));
  }

  obtenerComentarios(idRevista: number): Observable<any[]> {
    return this.http
      .get<any[]>(`${this.apiUrl}/comentarios?idRevista=${idRevista}`)
      .pipe(catchError(this.handleError<any[]>('obtenerComentarios', [])));
  }

  obtenerRevista(idRevista: string): Observable<Revista> {
    return this.http
      .get<Revista>(`${this.apiUrl}/revista?idRevista=${idRevista}`)
      .pipe(catchError(this.handleError<Revista>('obtenerRevista')));
  }

  obtenerAnuncios(usuario: string): Observable<Anuncio[]> {
    return this.http
      .get<Anuncio[]>(
        `${this.apiUrl}/Anuncios?usuario=${encodeURIComponent(usuario)}`,
        {
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .pipe(catchError(this.handleError<Anuncio[]>('obtenerAnuncios', [])));
  }

  obtenerRevistaSuscripciones(usuario: string): Observable<Revista[]> {
    return this.http
      .post<Revista[]>(`${this.apiUrl}/suscripcionRevistas`, usuario, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<Revista[]>('obtenerRevistaSuscripciones', []))
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
