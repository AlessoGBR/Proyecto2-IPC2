import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoRevistaComponent } from './estado-revista.component';

describe('EstadoRevistaComponent', () => {
  let component: EstadoRevistaComponent;
  let fixture: ComponentFixture<EstadoRevistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EstadoRevistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoRevistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
