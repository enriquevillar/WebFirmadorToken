import { TestBed } from '@angular/core/testing';

import { FirmadorLocalServiceService } from './firmador-local-service.service';

describe('FirmadorLocalServiceService', () => {
  let service: FirmadorLocalServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FirmadorLocalServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
