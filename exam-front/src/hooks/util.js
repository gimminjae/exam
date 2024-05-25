const util = {
  makeSearchParam(param) {
    let searchParams = []
    if (!(param === null || param === undefined)) {
      for (let key in param) {
        searchParams.push(`${key}=${param[key]}`)
      }
    }
    return `${searchParams.length === 0 ? '' : '?'}${searchParams.join('&')}`
  }
}

export default util
