import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrecioRevistaComponent } from './precio-revista.component';

describe('PrecioRevistaComponent', () => {
  let component: PrecioRevistaComponent;
  let fixture: ComponentFixture<PrecioRevistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrecioRevistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrecioRevistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
