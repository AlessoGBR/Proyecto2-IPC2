import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, of } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AnuncioService {
  private apiUrl = 'http://localhost:8080/Backend/resources/RegistroAnuncio';

  constructor(private http: HttpClient) {}

  registrarAnuncio(formData: any): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  ingresoCartera(monto: number, nombreUsuario: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = {
      monto: monto, 
      usuario: nombreUsuario,
    };
    return this.http.post(`${this.apiUrl}/cartera`, body, { headers });
  }

  ingresoCarteraEditor(monto: number, nombreUsuario: string): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const body = {
      monto: monto, 
      usuario: nombreUsuario,
    };
    return this.http.post(`${this.apiUrl}/carteraEditor`, body, { headers });
  }

  totalCartera(usuario: string): Observable<number> {
    return this.http
      .get<number>(
        `${this.apiUrl}/TotalCartera?usuario=${encodeURIComponent(usuario)}`,
        {
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .pipe(catchError(this.handleError<number>('totalCartera')));
  }

  totalCarteraEditor(usuario: string): Observable<number> {
    return this.http
      .get<number>(
        `${this.apiUrl}/TotalCarteraEditor?usuario=${encodeURIComponent(usuario)}`,
        {
          headers: { 'Content-Type': 'application/json' },
        }
      )
      .pipe(catchError(this.handleError<number>('totalCarteraEditor')));
  }

  actualizarPrecioAnuncio(precioAnuncio: {texto: number; imagen: number; video: number }): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/EditarPrecios`,
      precioAnuncio, 
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  preciosAnuncios(): Observable<{ texto: number; imagen: number; video: number }> {
    return this.http
      .get<{ texto: number; imagen: number; video: number }>(`${this.apiUrl}/PreciosAnuncios`, {
        headers: { 'Content-Type': 'application/json' },
      })
      .pipe(
        catchError(this.handleError<{ texto: number; imagen: number; video: number }>('preciosAnuncios', {
          texto: 0,
          imagen: 0,
          video: 0,
        }))
      );
  }
  
  

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
