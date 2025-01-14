import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, of } from 'rxjs';
import { Comentario } from 'app/Objetos/Comentario';
import { Suscripcion } from 'app/Objetos/Suscripcion';

@Injectable({
  providedIn: 'root',
})
export class RegistroService {
  private apiUrl = 'http://localhost:8080/Backend/resources/RegistroRevista';

  constructor(private http: HttpClient) {}

  registrarRevista(formData: FormData): Observable<any> {
    return this.http.post(this.apiUrl, formData);
  }

  verificarCartear(anuncios: boolean, usuario: string): Observable<any> {
    const body = {
      anuncios: anuncios,
      usuario: usuario,
    };

    return this.http.put<any>(
      `${this.apiUrl}/verificarCartera`,
      body,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  cambioComentario(idRevista: number, comentario: boolean): Observable<any> {
    const body = {
      idRevista: idRevista,
      tieneComentarios: comentario,
    };

    return this.http.put<any>(
      `${this.apiUrl}/actualizarComentarios/${idRevista}`,
      body,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  registrarComentario(comentario: Comentario): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/RegistrarComentario`,
      comentario,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  registrarSuscripcion(suscripcion: Suscripcion): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/RegistrarSuscripcion`,
      suscripcion,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  anularSuscripcion(suscripcion: Suscripcion): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/AnularSuscripcion`, suscripcion, {
      headers: { 'Content-Type': 'application/json' },
    });
  }

  cambioMeGusta(idRevista: number, comentario: boolean): Observable<any> {
    const body = {
      idRevista: idRevista,
      tieneMeGusta: comentario,
    };

    return this.http.put<any>(
      `${this.apiUrl}/actualizarMeGusta/${idRevista}`,
      body,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  cambioSuscripciones(idRevista: number, comentario: boolean): Observable<any> {
    const body = {
      idRevista: idRevista,
      tieneSuscripciones: comentario,
    };

    return this.http.put<any>(
      `${this.apiUrl}/actualizarSuscripciones/${idRevista}`,
      body,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  aceptarRevista(idRevista: number, precio: number): Observable<any> {
    const body = {
      idRevista: idRevista,
      precio: precio,
    };
    return this.http.put<any>(
      `${this.apiUrl}/actualizarAprobada/${idRevista}`,
      body,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  denegarRevista(idRevista: number, precio: number): Observable<any> {
    const body = {
      idRevista: idRevista,
      precio: precio,
    };

    return this.http.put<any>(
      `${this.apiUrl}/actualizarDenegada/${idRevista}`,
      body,
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  aceptarAnuncio(idAnuncio: number): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/actualizarAprobadaAnuncio/${idAnuncio}`,
      {},
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  denegarAnuncio(idAnuncio: number): Observable<any> {
    return this.http.put<any>(
      `${this.apiUrl}/actualizarDenegadaAnuncio/${idAnuncio}`,
      {},
      {
        headers: { 'Content-Type': 'application/json' },
      }
    );
  }

  actualizarPrecioRevista(idRevista: number, precio: number): Observable<any> {
    const body = { idRevista, precio }; 
  
    return this.http.put<any>(
      `${this.apiUrl}/CambioValorRevista`,
      body, 
      {
        headers: { 'Content-Type': 'application/json' }, 
      }
    );
  }

  actualizarPrecioAnuncio(idAnuncio: number, precio: number): Observable<any> {
    const body = { idAnuncio, precio }; 
  
    return this.http.put<any>(
      `${this.apiUrl}/CambioValorAnuncio`,
      body, 
      {
        headers: { 'Content-Type': 'application/json' }, 
      }
    );
  }

  preciosRevista(): Observable<{ precio: number}> {
      return this.http
        .get<{ precio: number}>(`${this.apiUrl}/PreciosRevistas`, {
          headers: { 'Content-Type': 'application/json' },
        })
        .pipe(
          catchError(this.handleError<{ precio: number }>('preciosAnuncios', {
            precio: 0
          }))
        );
    }

    actualizarPrecioRevistas(precioAnuncio: {precio: number}): Observable<any> {
      return this.http.put<any>(
        `${this.apiUrl}/EditarPrecios`,
        precioAnuncio, 
        {
          headers: { 'Content-Type': 'application/json' },
        }
      );
    }
  
     private handleError<T>(operation = 'operation', result?: T) {
        return (error: any): Observable<T> => {
          console.error(`${operation} failed: ${error.message}`);
          return of(result as T);
        };
      }
}
