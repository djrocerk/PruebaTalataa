import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TableDetalleOrderComponent } from './table-detalle-order.component';

describe('TableDetalleOrderComponent', () => {
  let component: TableDetalleOrderComponent;
  let fixture: ComponentFixture<TableDetalleOrderComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TableDetalleOrderComponent]
    });
    fixture = TestBed.createComponent(TableDetalleOrderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
