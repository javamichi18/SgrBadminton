import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SpielerDetailComponent } from './spieler-detail.component';

describe('SpielerDetailComponent', () => {
  let component: SpielerDetailComponent;
  let fixture: ComponentFixture<SpielerDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SpielerDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SpielerDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
