import { Injectable } from '@angular/core';
import { devEnv } from '../../../environments/dev';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { ResponseMessage } from '../model/response-message';

@Injectable({
  providedIn: 'root'
})
export class SuggestionService {
  readonly apiurl = devEnv.apiUrl + '/suggestion'

  constructor(private http:HttpClient) { }

  postSuggestion(suggestion:any) : Observable<ResponseMessage>{
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.post<ResponseMessage>(this.apiurl + '/', suggestion, {headers:headers}).pipe(map((resp:ResponseMessage)=>({  message:resp.message })));
  }
}
