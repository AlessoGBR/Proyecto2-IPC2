import { TestBed } from '@angular/core/testing';

import { CambioSuscripcionesService } from './cambio-suscripciones.service';

describe('CambioSuscripcionesService', () => {
  let service: CambioSuscripcionesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CambioSuscripcionesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
