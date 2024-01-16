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
  
  headers = new HttpHeaders({
    Authorization: `Bearer ${localStorage.getItem('token')}`
  })

  editAnswer(id:any, answer:any) : Observable<ResponseMessage>{
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.post<ResponseMessage>(this.apiurl + `/edit/${id}`, answer, {headers: headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }

  retrieveAnswersByPostId(id:any) : Observable<any>{
    //temporary till permissions fixed
    return this.http.get(this.apiurl + `/all?postId=${id}`)
  }

  upvoteAnswer(id:any){
    return this.http.post(this.apiurl + `/${id}/like`, null, {headers:this.headers})
  }

  downvoteAnswer(id:any){
    return this.http.post(this.apiurl + `/${id}/dislike`, null, {headers:this.headers})
  }

  deleteVote(id:any){
    return this.http.post(this.apiurl + `/deleteLikeOrDislike/${id}`, null, {headers:this.headers})
  }

  saveAnswer(answer:any) : Observable<ResponseMessage>{
    return this.http.post<ResponseMessage>(this.apiurl, answer, {headers: this.headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }

  getAnswerById(id:any) : Observable<any>{
    return this.http.get(this.apiurl + `/${id}`);
  }

  getSuggestionsForAnswer(id:any) : Observable<any>{
    return this.http.get(this.apiurl + `/${id}/suggestions`, {headers: this.headers});
  }

  acceptSuggestion(id:any, suggestionId:any) : Observable<ResponseMessage>{
    return this.http.put<ResponseMessage>(this.apiurl + `/${id}/accept/${suggestionId}`, null, {headers: this.headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }

  getHistoryForAnswer(id:any) : Observable<any>{
    return this.http.get(this.apiurl + `/${id}/history`, {headers: this.headers});
  }

  deleteAnswer(answerId: string) : Observable<ResponseMessage>{
    return this.http.delete<ResponseMessage>(this.apiurl + '/delete/' + answerId, {headers: this.headers})
    .pipe(map((resp:ResponseMessage)=>({  message:resp.message })))
  }
}
