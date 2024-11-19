import { TestBed } from '@angular/core/testing';
import { ImgBrokenDirective } from './img-broken.directive';
import { ElementRef } from '@angular/core'; 

describe('ImgBrokenDirective', () => {
  it('should create an instance', () => {
    const elementRefMock = {
      nativeElement: document.createElement('img')
    };
    const directive = new ImgBrokenDirective(new ElementRef(elementRefMock));
    expect(directive).toBeTruthy();
  });
});
