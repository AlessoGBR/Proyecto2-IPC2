import { TestBed } from '@angular/core/testing';

import { ObtenerObjetosService } from './obtener-objetos.service';

describe('ObtenerObjetosService', () => {
  let service: ObtenerObjetosService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ObtenerObjetosService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
