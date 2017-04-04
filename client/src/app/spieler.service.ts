///<reference path="../../node_modules/@angular/http/src/interfaces.d.ts"/>
/**
 * Created by Max on 28.03.2017.
 */
import {Spieler} from "./spieler";
import {Injectable} from "@angular/core";

import {Headers, Http, RequestMethod, RequestOptions, Response} from "@angular/http";
import {Observable} from "rxjs/Observable";
import "rxjs/add/operator/catch";
import "rxjs/add/operator/map";


@Injectable()
export class SpielerService {

    constructor(private http: Http) {
    }

    /*private obsSpielerholen(): Observable<Spieler[]> {
     let spielerUrl = 'http://localhost:11111/sgrbadminton/rest/rangliste';
     let response2: Response;
     let test = this.jsonp.get(spielerUrl).map(response => response2 = response.json());

     self.window.console.log(response2);
     return null;
     }*/
    public Spielerholen(): Spieler[] {
        let spieler: Spieler[];
        this.obsSpielerholen()
        //     .subscribe(
        //     sp => spieler = <Spieler[]> sp
        // );
            .subscribe(
                function (response) {
                    console.log("Success Response" + response);
                    spieler = response;
                },
                function (error) {
                    console.log("Error happened" + error)
                },
                function () {
                    console.log("the subscription is completed")
                    return spieler;
                }
            );


        return null;
    }

    public obsSpielerholen(): Observable<Spieler[]> {
        let spielerUrl = 'http://localhost:11111/sgrbadminton/rest/rangliste';

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
        self.window.console.log('XX' + body.spieler);
        return body.spieler || {};
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
