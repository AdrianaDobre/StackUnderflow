import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { devEnv } from '../../../environments/dev';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http:HttpClient) { }

  private readonly apiurl = devEnv.apiUrl + '/auth';

  proceedLogin(user:any) : Observable<AuthResponse>{
    return this.http.post<AuthResponse>(this.apiurl+"/login", user).pipe(map((r:AuthResponse)=>({
      token:r.token
    })))
  }

  proceedRegister(userRegister:any) : Observable<AuthResponse>{
    return this.http.post<AuthResponse>(this.apiurl+"/register", userRegister).pipe(map((r:AuthResponse)=>({
      token:r.token
    })))
  }

  getEmailFromToken(){
    let token =  localStorage.getItem('token')!
    let decodedJWT = JSON.parse(window.atob(token.split('.')[1]))
    if (this.isLoggedIn()) {
      return decodedJWT.sub;
    }
    return ""
  }

  getUserIdFromToken(){
    let token =  localStorage.getItem('token')!
    let decodedJWT = JSON.parse(window.atob(token.split('.')[1]))
    if (this.isLoggedIn()) {
      return decodedJWT.id;
    }
    return ""
  }

  isLoggedIn(){
    return localStorage.getItem('token') != null;
  }

  logOut() { 
    localStorage.removeItem('token');
    window.location.reload();
  }
}

//too lazy to make a different file
export interface AuthResponse{
  token:string
}
