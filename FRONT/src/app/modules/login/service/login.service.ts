import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError, catchError, BehaviorSubject, tap, map } from 'rxjs';
import { LoginRequest } from 'src/app/core/models/login.interface';
import { environment } from 'src/environment/enviroment';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  currentUserLoginOn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  currentUserData: BehaviorSubject<String> = new BehaviorSubject<String>("");

  constructor(private http: HttpClient) { 
    this.currentUserLoginOn = new BehaviorSubject<boolean>(sessionStorage.getItem("token") != null);
    this.currentUserData = new BehaviorSubject<String>(sessionStorage.getItem("token") || "");
  }

  login(credentials: LoginRequest): Observable<any> {
    return this.http.post<any>(environment.api + "/security/login", credentials).pipe(
      tap((userData) => {
        sessionStorage.setItem("token", userData.token);
        sessionStorage.setItem("role", userData.role);
        sessionStorage.setItem("frecuencia", userData.frecuencia);
        this.currentUserData.next(userData.token);
        this.currentUserLoginOn.next(true);
      }),
      map((userData) => userData.token),
      catchError(this.handleError)
    );
  }

  logout(): void {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("frecuencia");
    this.currentUserLoginOn.next(false);
  }

  private handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      console.error('Se ha producido un error ', error.error);
    } else {
      console.error('Backend retornó el código de estado ', error);
    }
    return throwError(() => new Error('Algo falló. Por favor intente nuevamente.'));
  }

  getuserData(): Observable<String> {
    return this.currentUserData.asObservable();
  }

  getuserLoginOn(): Observable<boolean> {
    return this.currentUserLoginOn.asObservable();
  }

  getuserToken(): String {
    return this.currentUserData.value;
  }

  getToken(): string | null {
    return sessionStorage.getItem('token');
  }

  getRole(): string | null {
    return sessionStorage.getItem('role');
  }

  getFrecuencia(): number | null {
    const frecuencia = sessionStorage.getItem('frecuencia');
    return frecuencia ? parseInt(frecuencia, 10) : null;
  }

  updateFrecuencia(newFrecuencia: number): void {
    sessionStorage.setItem('frecuencia', newFrecuencia.toString());
    this.currentUserData.next(newFrecuencia.toString());
  }
  
  
}
