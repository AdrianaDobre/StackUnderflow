import { Injectable } from '@angular/core';
import { devEnv } from '../../../environments/dev';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ResponseMessage } from '../model/response-message';

@Injectable({
  providedIn: 'root'
})
export class PostService {

  private readonly apiurl = devEnv.apiUrl + '/post';

  constructor(private http:HttpClient) { }

  createPost(post:any) : Observable<ResponseMessage>{
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.post<ResponseMessage>(this.apiurl, post, {headers: headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }

  retrievePostById(id:any) : Observable<any>{
    return this.http.get(this.apiurl + `/${id}`)
  }
}
