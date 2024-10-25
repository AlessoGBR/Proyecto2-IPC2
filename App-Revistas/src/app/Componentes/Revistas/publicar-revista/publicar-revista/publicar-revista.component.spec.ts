import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PublicarRevistaComponent } from './publicar-revista.component';

describe('PublicarRevistaComponent', () => {
  let component: PublicarRevistaComponent;
  let fixture: ComponentFixture<PublicarRevistaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PublicarRevistaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PublicarRevistaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
