import { Component, OnInit } from '@angular/core';
import {LoaderService} from "@app/services/load.service";

@Component({
  selector: 'app-my-loader',
  templateUrl: './my-loader.component.html',
  styleUrls: ['./my-loader.component.scss']
})
export class MyLoaderComponent implements OnInit {
  loading: boolean=false;
  color = 'primary';
  mode = 'indeterminate';
  value = 50;
  constructor(public loaderService: LoaderService) {


  }

  ngOnInit(): void {
      this.loaderService.isLoading.subscribe((v) => {
      console.log(v);
      this.loading = v;
    });
  }

}
