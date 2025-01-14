import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PrecioAnuncioComponent } from './precio-anuncio.component';

describe('PrecioAnuncioComponent', () => {
  let component: PrecioAnuncioComponent;
  let fixture: ComponentFixture<PrecioAnuncioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PrecioAnuncioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PrecioAnuncioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
