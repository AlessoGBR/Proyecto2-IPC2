import { TestBed } from '@angular/core/testing';

import { CambioMeGustaService } from './cambio-me-gusta.service';

describe('CambioMeGustaService', () => {
  let service: CambioMeGustaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CambioMeGustaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
