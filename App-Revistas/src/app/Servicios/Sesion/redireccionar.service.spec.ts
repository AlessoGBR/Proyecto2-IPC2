import { TestBed } from '@angular/core/testing';

import { RedireccionarService } from './redireccionar.service';

describe('RedireccionarService', () => {
  let service: RedireccionarService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RedireccionarService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
