import { TestBed } from '@angular/core/testing';
import { CanActivateFn } from '@angular/router';

import { onTokenGuard } from './on-token.guard';

describe('onTokenGuard', () => {
  const executeGuard: CanActivateFn = (...guardParameters) => 
      TestBed.runInInjectionContext(() => onTokenGuard(...guardParameters));

  beforeEach(() => {
    TestBed.configureTestingModule({});
  });

  it('should be created', () => {
    expect(executeGuard).toBeTruthy();
  });
});
