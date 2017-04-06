/**
 * Created by Max on 06.04.2017.
 */
import {Injectable} from "@angular/core";

import {Headers, Http, RequestMethod, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map"
import {Spiel} from "./spiel";

@Injectable()
export class ForderungenService {
    constructor(private http: Http) {
    }

    public obsForderungenholen(): Observable<Spiel[]> {
        let spielerUrl = 'http://localhost:11111/sgrbadminton/rest/forderungen';

        let options: RequestOptions = new RequestOptions(RequestMethod.Get);
        if (options.headers == null) {
            options.headers = new Headers();
        }
        options.headers.append('Content-Type', 'application/json');
        options.headers.append('Content-Security-Policy', 'script-src * "unsafe-eval"');
        //options.headers.append('Access-Control-Allow-Origin' ,'http://localhost:11111/sgrbadminton/rest/rangliste');

        let test = this.http.get(spielerUrl, options)
            .map(this.extractData)
            .catch(this.handleError);
        return test;
    }

    private extractData(res: Response) {
        let body = res.json();
        return body.forderungen || {};
    }

    private handleError(error: any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
    }
}
