import "rxjs/add/operator/switchMap";
import {Component, OnInit} from "@angular/core";
import {ActivatedRoute, Params} from "@angular/router";
import {SpielerService} from "../spieler.service";
import {Spieler} from "../../spieler";

@Component({
    selector: 'app-spieler-detail',
    templateUrl: './spieler-detail.component.html',
    providers: [SpielerService]
})
export class SpielerDetailComponent implements OnInit {
    private spieler: Spieler;
    private _spielerService: SpielerService;

    constructor(private route: ActivatedRoute, private spielerService: SpielerService) {
        this._spielerService = spielerService;
    }

    ngOnInit() {

        let test : Spieler;
        this._spielerService.getSpielerDetails(1)
            .then(val => test = val);
        self.console.log("XX "+test);
        this.spieler = test;
        this.route.params;
            //.switchMap((params: Params) => this.spielerService.getSpielerDetailsPlain(+params['id']))
            //.subscribe(spieler => this.spieler = spieler);
    }

    /*
     ngOnInit(): void {
     this.route.params
     .switchMap((params: Params) => this.heroService.getHero(+params['id']))
     .subscribe(hero => this.hero = hero);
     } */

}
