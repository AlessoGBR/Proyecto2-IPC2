import { TestBed } from '@angular/core/testing';
import { RegistroService } from './Registro.service';

describe('RegistroService', () => {
    let servicio: RegistroService;

    beforeEach(() =>{
        TestBed.configureTestingModule({});
        servicio = TestBed.inject(RegistroService);
    });

    it('should be created' , () => {
        expect(servicio).toBeTruthy();
    });
});
