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

  editPost(id:any, post:any) : Observable<ResponseMessage>{
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.put<ResponseMessage>(this.apiurl + `/edit/${id}`, post, {headers: headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }

  getPostById(id:any) : Observable<any>{
    return this.http.get(this.apiurl + `/${id}`)
  }

  getAllPosts() : Observable<any>{
    return this.http.get(this.apiurl + `/all`)
  }

  deletePost(postId: string) : Observable<ResponseMessage>{
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.delete<ResponseMessage>(this.apiurl + '/delete/' + postId, {headers: headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }

  pickBest(postId: string, answerId: string) : Observable<ResponseMessage> {
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.put<ResponseMessage>(this.apiurl + '/' + postId + '/bestAnswer/' + answerId, {headers: headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }
}
