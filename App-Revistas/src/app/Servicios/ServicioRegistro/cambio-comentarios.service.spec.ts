import { TestBed } from '@angular/core/testing';

import { CambioComentariosService } from './cambio-comentarios.service';

describe('CambioComentariosService', () => {
  let service: CambioComentariosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CambioComentariosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
