import { Injectable } from '@angular/core';
import { devEnv } from '../../../environments/dev';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private readonly apiurl = devEnv.apiUrl + '/topic';

  constructor(private http:HttpClient) { }

  getAllTopics() : Observable<any>{
    //temporary till permissions fixed
    const headers = new HttpHeaders({
      Authorization: `Bearer ${localStorage.getItem('token')}`
    })
    return this.http.get(this.apiurl+"/all", {headers:headers});
  }
}
