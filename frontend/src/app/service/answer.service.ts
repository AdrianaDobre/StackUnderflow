import { Injectable } from '@angular/core';
import { devEnv } from '../../../environments/dev';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ResponseMessage } from '../model/response-message';


@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  readonly apiurl = devEnv.apiUrl + '/answer'

  constructor(private http:HttpClient) { }

  retrieveAnswersByPostId(id:any) : Observable<any>{
    //temporary till permissions fixed
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.get(this.apiurl + `/all?postId=${id}`, {headers:headers})
  }

  saveAnswer(answer:any) : Observable<ResponseMessage>{
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })

    return this.http.post<ResponseMessage>(this.apiurl, answer, {headers: headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }
}
