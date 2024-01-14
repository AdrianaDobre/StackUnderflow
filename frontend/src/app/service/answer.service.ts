import { Injectable } from '@angular/core';
import { devEnv } from '../../../environments/dev';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AnswerService {
  readonly apiurl = devEnv.apiUrl + '/answer'

  constructor(private http:HttpClient) { }
  
  headers = new HttpHeaders({
    Authorization: `Bearer ${localStorage.getItem('token')}`
  })

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
}
